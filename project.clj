(defproject updcon/clj-pulsar "0.0.1-SNAPSHOT"
  :description "A Clojure library to work with Apache Pulsar"
  :url "https://github.com/updcon/clj-pulsar"
  :license {:name "MIT License"}

  :main clj-pulsar.core

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.apache.pulsar/pulsar-client "2.2.0"]
                 [org.apache.pulsar/pulsar-client-admin "2.2.0"]

                 ;; Loggers
                 ;; -------
                 [com.taoensso/timbre "4.10.0"]
                 [com.fzakaria/slf4j-timbre "0.3.12"]
                 [org.slf4j/slf4j-api "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]
                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [net.tbt-post/sentry-tiny "0.1.7"]])
