(ns wevre.benchmark
  (:require [criterium.core :refer [bench]]
            [wevre.impl.natural-compare-lazy :as nc]
            [wevre.impl.natural-compare-parse :as nc-parse]
            [wevre.impl.natural-compare-bigint :as nc-bigint]
            [wevre.impl.natural-compare-lucio :as nc-lucio]
            [wevre.impl.natural-compare-pakhomov :as nc-pakhomov]))

(def l1 ["t3" "t1" "t10" "t12" "t2" "t27"])

(def l2 (into []
              (map #(str "seat-" %))
              (take 500 (repeatedly #(rand-int 1000)))))

(def l3 (->> (repeatedly #(rand-int 1000))
             (partition 20)
             (map (fn [x] (apply str (interpose "ABC" x))))
             (take 500)
             vec))

(comment

  ;; The numbers after each benchmark are the results for L1, L2, and L3.

  (let [t l3]
    (println "lazy")
    (bench (sort nc/natural-compare t)) ; 19µs :: 15ms :: 8ms

    (println "parse")
    (bench (sort nc-parse/natural-compare t)) ; 14µs :: 6ms :: 38ms

    (println "lucio")
    (bench (sort nc-lucio/natural-compare t)) ; 20µs :: 9ms :: 66ms

    (println "pakhomov")
    (bench (sort nc-pakhomov/natural-compare t)) ; 15µs :: 6ms :: 42ms

    (println "bigint")
    (bench (sort nc-bigint/natural-compare t)) ; 27µs :: 11ms :: 134ms
    )
  )
