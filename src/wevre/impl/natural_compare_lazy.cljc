(ns ^{:author "Mike Weaver"
      :doc "A comparator for natural sorting of strings. Implements a custom
            integer comparison (instead of parse-int) to avoid overflow."}
 wevre.impl.natural-compare-lazy)

(def digit #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9})
(def non-digit (complement digit))

(defn- add-nil [a] (cond-> a (empty? a) (concat [nil])))

(defn natural-compare
  ([a b] (natural-compare compare a b (cycle [true false])))
  ([cmp a b [text? & text?'s]]
   (let [pred (if text? non-digit digit)
         [a-take a-drop] (split-with pred a)
         [b-take b-drop] (split-with pred b)
         [a-take b-take] (if text?
                           [(add-nil a-take) (add-nil b-take)]
                           (cond
                             (and (empty? a-take) (empty? b-take)) [a                 b]
                             (or (empty? a-take) (empty? b-take))  [(add-nil a-take) (add-nil b-take)]
                             :else
                             ,, (let [d (- (count b-take) (count a-take))]
                                  [(concat (repeat (max 0 d) \0) a-take)
                                   (concat (repeat (max 0 (- d)) \0) b-take)])))
         result (or (->> (map cmp a-take b-take) (drop-while zero?) first) 0)]
     (cond
       (and (empty? a-take) (empty? b-take)) 0
       (empty? a-take)                       +1
       (empty? b-take)                       -1
       (zero? result)                        (recur cmp a-drop b-drop text?'s)
       :else                                 result))))

(comment
  (sort natural-compare ["A0" "A"])
  )
