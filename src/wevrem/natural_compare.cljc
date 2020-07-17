(ns wevrem.natural-compare
  ^{:author "Mike Weaver"
    :see-also [["https://gist.github.com/wilkerlucio/db54dc83a9664124f3febf6356f04509"
                "Adapted from a gist by Wilker Lúcio -- Alphabetical/Natural sorting in Clojure/Clojurescript"]]
    :doc "A comparator for natural sorting of strings. Internal digits compare as integers."}
  (:require [clojure.string :as str]))

(defn- split-digits
  "Splits string into sequence of alternating text and numbers."
  [s]
  (let [parse-int #?(:clj #(Long/parseLong %) :cljs #(js/parseInt %)) 
        text (str/split s #"\d+")
        nums (mapv parse-int (re-seq #"\d+" s))]
    ;; Padding `text` and `nums` with "" and -1, respectively, ensures resulting
    ;; sequence will start with text, end with a number, and consistently 
    ;; alternate inbetween. The pad values sort lower than 'legit' values, so
    ;; when one string is a prefix of another, the shorter one sorts first.
    (interleave (conj text "") (conj nums -1))))

(defn natural-compare
  "Compares two strings using natural sorting."
  [a b]
  (or (->> (map compare (split-digits a) (split-digits b))
           (drop-while zero?)
           first)
      0))
