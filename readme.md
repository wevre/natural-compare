# natural-compare

A natural-sort compatator for strings. Sorts embedded digits as integers, so
strings like `["v12" "v2"]` will sort 'naturally' as you would expect:

    (sort ["v12" "v2"])
    user=> '("v12" "v2")
    (sort natural-compare ["v12" "v2"])
    user=> '("v2" "v12")
