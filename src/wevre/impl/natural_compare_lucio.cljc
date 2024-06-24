(ns ^{:author "Wilker Lúcio"
      :see-also [["https://gist.github.com/wilkerlucio/db54dc83a9664124f3febf6356f04509"
                  "Adapted from a gist by Wilker Lúcio -- Alphabetical/Natural sorting in Clojure/Clojurescript"]]
      :doc "A comparator for natural sorting of strings, written by Wilker Lúcio."}
 wevre.impl.natural-compare-lucio
  (:require [clojure.string :as str]))

(defn parse-int [s]
  #?(:clj (Long/parseLong s)
     :cljs (js/parseInt s)))

(defn vector-compare [[value1 & rest1] [value2 & rest2]]
  (let [result (compare value1 value2)]
    (cond
      (not (zero? result)) result
      (nil? value1) 0
      :else (recur rest1 rest2))))

(defn prepare-string [s]
  (let [s (or s "")
        parts (vec (str/split s #"\d+"))
        numbers (->> (re-seq #"\d+" s)
                     (map parse-int)
                     (vec))]
    (vec (interleave (conj parts "") (conj numbers -1)))))

(defn natural-compare [a b]
  (vector-compare (prepare-string a)
                  (prepare-string b)))
