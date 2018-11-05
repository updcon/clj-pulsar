(ns clj-pulsar.core
  (:require [taoensso.timbre :as log]
            [clojure.java.io :as io])
  (:import (org.apache.pulsar.client.api PulsarClient Producer Consumer)
           (org.apache.pulsar.client.impl ClientBuilderImpl)
           (java.util.concurrent TimeUnit)))

(defn ^PulsarClient connect [^String url]
  (-> (ClientBuilderImpl.)
      (.serviceUrl url)
      (.build)))

(defn disconnect [^PulsarClient client]
  (.shutdown client))

(defn ^Producer producer [^PulsarClient client ^String topic]
  (-> (.newProducer client)
      (.topic topic)
      (.create)))

(defn ^Consumer consumer [^PulsarClient client ^String topic ^String group]
  (-> (.newConsumer client)
      (.topic (into-array String [topic]))
      (.subscriptionName group)
      (.ackTimeout 60 TimeUnit/SECONDS)
      (.subscribe)))

(defn msg->topic [^Producer p msg]
  (.send ^Producer p (.getBytes msg)))

(defn msg<-topic [^Consumer c]
  (when-let [msg (.receive c)]
    (.acknowledge c msg)
    (.getData msg)))

(defn -main [& args]
  (log/info "connecting to:" (first args))
  (let [url (first args)
        topic (second args)
        cl (connect url)]
    (log/debug "CLIENT:" cl)
    (with-open [p (producer cl topic)]
      (log/debug "PRODUCING VIA:" p)
      (msg->topic p "test")
      (log/debug "MESSAGE SENT!"))
    (with-open [c (consumer cl topic "my-group")]
      (log/debug "CONSUMING WITH:" c)
      (when-let [msg (msg<-topic c)]
        (log/debug "MESSAGE TYPE:" (type msg))
        (log/debug "GOT MESSAGE:" (String. msg))))
    (log/debug "DISCONNECTING CLIENT:" cl)
    (disconnect cl)))
