(ns ^{:author "Eugene Pakhomov"
      :see-also [["https://gist.github.com/wilkerlucio/db54dc83a9664124f3febf6356f04509?permalink_comment_id=5096530#gistcomment-5096530"
                  "FWIW, I ended up with this version. On my benchmarks, it was faster by around 30-50%."]]
      :doc "A comparator for natural sorting of strings proposed by @p-himik."}
 wevre.impl.natural-compare-pakhomov
  (:refer-clojure :exclude [parse-long]))

(def parse-long #?(:clj #(Long/parseLong %) :cljs #(js/parseInt %)))

(defn vector-compare [[value1 & rest1] [value2 & rest2]]
  (let [result (compare value1 value2)]
    (cond
      (not (zero? result)) result
      (nil? value1) 0
      :else (recur rest1 rest2))))

(defn prepare-string
  "Splits the string `s` into a vector of alternating
  numbers-strings where the first item is always a string,
  potentially empty."
  [s]
  (when (seq s)
    (let [parts (vec (re-seq #"\d+|\D+" s))
          first-number? (re-matches #"\d" (subs (nth parts 0) 0 1))]
      (if first-number?
        (into [""]
              (map-indexed (fn [idx part]
                             (cond-> part (even? idx) (parse-long))))
              parts)
        (into []
              (map-indexed (fn [idx part]
                             (cond-> part (odd? idx) (parse-long))))
              parts)))))

(defn natural-compare [a b]
  (vector-compare (prepare-string a)
                  (prepare-string b)))
