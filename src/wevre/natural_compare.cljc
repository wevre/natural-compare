(ns ^{:author "Mike Weaver"
      :see-also [["https://gist.github.com/wilkerlucio/db54dc83a9664124f3febf6356f04509"
                  "Adapted from a gist by Wilker Lúcio -- Alphabetical/Natural sorting in Clojure/Clojurescript"]]
      :doc "A comparator for natural sorting of strings. Internal digits compare as integers."}
 wevre.natural-compare
  (:require [wevre.impl.natural-compare-parse :as impl]))

(defn natural-compare
  "Compares two strings using natural, as opposed to lexical, sorting."
  [a b]
  (impl/natural-compare a b))

(comment
  (sort compare ["A1" "A"])
  )
