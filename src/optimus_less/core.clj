(ns optimus-less.core
  (:require [clojure.string :as str]
            [optimus.assets.load-css :refer [create-css-asset]]
            [optimus.assets.creation :refer [last-modified existing-resource]]
            [clojure.core.cache :as cache])
  (:import [com.github.sommeri.less4j.core DefaultLessCompiler]))

; The cache maps less code to css code
(def compiled-cache
  (atom (cache/fifo-cache-factory {} :threshold 50)))

(defn compile-less [less]
  (-> (DefaultLessCompiler.)
      (.compile less)
      (.getCss)))

(defn get-compiled-less [less]
  (get (swap!
         compiled-cache
         #(cache/through
            compile-less
            %
            less))
       less))

(defn load-less-asset [public-dir path]
  (let [resource (existing-resource public-dir path)]
    (-> (create-css-asset (str/replace path #"\.less$" ".css")
                          (get-compiled-less resource)
                          (last-modified resource))
        (assoc :original-path path))))

(defmethod optimus.assets.creation/load-asset "less"
  [public-dir path]
  (load-less-asset public-dir path))
