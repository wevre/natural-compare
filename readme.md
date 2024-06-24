# natural-compare

[![cljdoc badge](https://cljdoc.org/badge/wevre/natural-compare)](https://cljdoc.org/d/wevre/natural-compare)
[![bb compatible](https://raw.githubusercontent.com/babashka/babashka/master/logo/badge.svg)](https://babashka.org)

A natural-sort [comparator](https://clojure.org/guides/comparators) for strings
in Clojure/Script. Treats embedded digits as integers, so strings like `["v12"
"v2"]` will receive a natural sort, as opposed to a lexical sort:

```clj
(def v ["v12" "v2"])

(sort v)
;;=> ("v12" "v2")   ;; lexical sort

(sort natural-compare v)
;;=> ("v2" "v12")   ;; natural sort
```

# Install

deps.edn

    wevre/natural-compare {:mvn/version "0.0.10"}

project.clj

    [wevre/natural-compare "0.0.10"]

Or, and this might be the easiest, just copy the body of
`impl/natural_compare_parse.cljc` directly into your project.

# How to use

```clj
(require '[wevre.natural-compare :refer [natural-compare])

(def ss ["t3" "t1" "t10" "t12" "t2" "t27"])

(sort natural-compare ss)
;;=> ("t1" "t2" "t3" "t10" "t12" "t27")

(sort (comp - natural-compare) ss)
;;=> ("t27" "t12" "t10" "t3" "t2" "t1")

```

# How it works

`natural-compare` operates on strings. Each is split into a sequence of
alternating text and integer elements (always starting with a text element) and
then the two sequences are compared element by element. The elements are padded
as necessary with values that always sort lower than 'legit' values, to ensure
shorter strings sort first.

See test cases for more examples.

# Where it came from

Adapted from a gist (and subsequent comments) by Wilker Lúcio -- [`Alphabetical/Natural sorting in
Clojure/Clojurescript`](https://gist.github.com/wilkerlucio/db54dc83a9664124f3febf6356f04509)

Reading those comments, you'll see there is some discussion about limitations
(overflow) and performance. This library actually contains a few different
implementations taken from that discussion. Based on my rudimentary benchmarks
so far, I have drawn these conclusions:

* For shorter strings, the implementations based on parsing integers are
  fastest, but they have the potential limitation to overflow on (string
  representations of) large integer inputs.

* The "lazy" implementation outperforms the others on large strings where it can
  stop early without needing to split and parse the entire string.

* It depends on your use case, but I feel that most situations where a natural
  sort order is desired are about small strings (like filenames or labels), so
  the concerns about overflow or laziness are (in my experience) not as
  important.

* The default implemenation used in this library ("parse") is based on parsing
  integers and will overflow. If you need something different for your use case,
  check out one of the other implementations.

# What's next

Something I would consider useful, but haven't needed yet in my own projects:
properly sort strings that are date names, like "MON", "TUE", …; "JAN", "FEB",
….



# License

Copyright © Mike Weaver

Licensed under the terms of Ecplise Public License 2.0, see license.txt.
