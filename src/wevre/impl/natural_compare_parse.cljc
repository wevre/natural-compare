(ns ^{:author "Mike Weaver"
      :doc "A comparator for natural sorting of strings. Internal digits compare
            as integers."}
 wevre.impl.natural-compare-parse
  (:require [clojure.string :as str]))

(defn- split-digits
  "Splits string into sequence of alternating text and numbers."
  [s]
  (let [parse-int #?(:clj #(Long/parseLong %) :cljs #(js/parseInt %))
        text (str/split s #"\d+")
        nums (mapv parse-int (re-seq #"\d+" s))]
    (interleave (conj text "") (conj nums -1))))

(defn natural-compare
  "Compares two strings using natural sorting."
  ([a b]
   (natural-compare compare a b))

  ([cmp a b]
   (or (->> (map cmp (split-digits a) (split-digits b))
            (drop-while zero?)
            first)
       0)))