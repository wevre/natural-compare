(ns wevre.natural-compare-test
  (:require [wevre.natural-compare :as sut]
            [clojure.test :refer [deftest testing are is]]))

(deftest natural-compare
  (testing "split digits"
    (are [s out] (= out (#'sut/split-digits s))
      ""     ["" -1]
      "0"    ["" 0]
      "C"    ["C" -1]
      "a1b"  ["a" 1 "b" -1]
      "a1b2" ["a" 1 "b" 2 "" -1]))

  (testing "sort"
    (are [coll out] (= out (sort sut/natural-compare coll))
      ["0" ""]                           ["" "0"]
      ["C0" "C"]                         ["C" "C0"]
      ["a1b2c3" "a1b2"]                  ["a1b2" "a1b2c3"]
      ["a1b2c3" "a1b2c"]                 ["a1b2c" "a1b2c3"]
      ["t3" "t1" "t10" "t12" "t2" "t27"] ["t1" "t2" "t3" "t10" "t12" "t27"]))

  (testing "custom comparator"
    (let [descending (fn [a b] (- (compare a b)))]
      (is (= ["A10" "A9" "A5"]
             (sort (partial sut/natural-compare descending) ["A9" "A5" "A10"]))))))
