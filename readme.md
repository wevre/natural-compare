# natural-compare

[![cljdoc badge](https://cljdoc.org/badge/wevre/natural-compare)](https://cljdoc.org/d/wevre/natural-compare)
[![bb compatible](https://raw.githubusercontent.com/babashka/babashka/master/logo/badge.svg)](https://babashka.org)

A natural-sort [comparator](https://clojure.org/guides/comparators) for strings
in Clojure/Script. Sorts embedded digits as integers, so strings like `["v12"
"v2"]` will sort 'naturally' as you would expect:

```clj
(def v ["v12" "v2"])

(sort v)
;;user=> ("v12" "v2")   ;; lexical sort

(sort natural-compare v)
;;user=> ("v2" "v12")   ;; 'natural' sort
```

# Install

deps.edn

    wevre/natural-compare {:mvn/version "0.0.8"}

project.clj

    [wevre/natural-compare "0.0.8"]

Or, and this might be the easiest, just copy the two functions in
`natural_compare.cljc` directly into your project.

# How to use

```clj
(require '[wevre.natural-compare :refer [natural-compare]])

(def ss ["t3" "t1" "t10" "t12" "t2" "t27"])

(sort natural-compare ss)

;;=> ("t1" "t2" "t3" "t10" "t12" "t27")
```

# How it works

`natural-compare` operates on strings. Each is split into a sequence of
alternating text and integer elements and then the two sequences are compared
element by element. The trick is to make sure that when splitting the string,
the first element is always text, the elements always alternate between text and
integer, and the last element is an integer. The sequence is padded as necessary
with values `""` and `-1` (which always sort lower than 'legit' values) to make
sure shorter strings sort first.

See test cases for more examples.

Note the comparator uses `Long/parseLong` (or `js/parseInt` for cljs) and as
such will choke on integer strings that overflow. Handling that, if needed, is
left as an exercise for the reader. :-) While you're at it, another thing you
might consider is what to do when you encounter strings like "MON", "TUE", etc.
or "JAN", "FEB", etc.—if, you know, you need that sort of capability.

# Where it came from

Adapted from a gist (and subsequent comments) by Wilker Lúcio -- [`Alphabetical/Natural sorting in
Clojure/Clojurescript`](https://gist.github.com/wilkerlucio/db54dc83a9664124f3febf6356f04509)

# License

Copyright © Mike Weaver

Licensed under the terms of Ecplise Public License 2.0, see license.txt.
