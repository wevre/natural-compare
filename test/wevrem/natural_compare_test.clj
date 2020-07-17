(ns wevrem.natural-compare-test
  (:require [wevrem.natural-compare :as sut]
            [clojure.test :as t]))

(t/deftest test-split-digits
  (t/are [s out]
       (= out (#'sut/split-digits s))
    ""  ["" -1]
    "0" ["" 0]
    "C" ["C" -1]
    "a1b" ["a" 1 "b" -1]
    "a1b2" ["a" 1 "b" 2 "" -1]))

(t/deftest test-sort
  (t/are [coll out]
       (= out (sort sut/natural-compare coll))
    '("0" "") '("" "0")
    '("C0" "C") '("C" "C0")
    '("a1b2c3" "a1b2") '("a1b2" "a1b2c3")
    '("a1b2c3" "a1b2c") '("a1b2c" "a1b2c3")
    '("t3" "t1" "t10" "t12" "t2" "t27") `("t1" "t2" "t3" "t10" "t12" "t27")))
