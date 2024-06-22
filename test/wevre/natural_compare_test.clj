(ns wevre.natural-compare-test
  (:require [wevre.natural-compare :as sut]
            [clojure.test :refer [deftest testing are is]]))

(deftest natural-compare
  (testing
   (are [coll out] (= out (vec (sort sut/natural-compare coll)))
     ["0" ""]                           ["" "0"]
     ["C0" "C"]                         ["C" "C0"]
     ["a1b2c3" "a1b2"]                  ["a1b2" "a1b2c3"]
     ["a1b2c3" "a1b2c"]                 ["a1b2c" "a1b2c3"]
     ["12" "2"]                         ["2" "12"]
     ["t3" "t1" "t10" "t12" "t2" "t27"] ["t1" "t2" "t3" "t10" "t12" "t27"]
     ["0010" "0001"]                    ["0001" "0010"]
     ["Märrklobb 20" "Märrklobb 1"]     ["Märrklobb 1" "Märrklobb 20"]
     ["10" "001"]                       ["001" "10"]))

  (testing "custom comparator"
    (is (= ["A10" "A9" "A5"]
           (sort (comp - sut/natural-compare) ["A9" "A5" "A10"])))))
