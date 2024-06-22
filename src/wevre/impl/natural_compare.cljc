(ns wevre.impl.natural-compare)

(def digit #{\0 \1 \2 \3 \4 \5 \6 \7 \8 \9})
(def non-digit (complement digit))

(defn- pre-pad [a b]
  (let [cnt-a (count a) cnt-b (count b)
        c (if (and (< 0 cnt-a) (< 0 cnt-b)) \0 \u0000)
        d (- cnt-b cnt-a)]
    [(concat (repeat (max 0 d) c) a)
     (concat (repeat (max 0 (- d)) c) b)]))

(defn- post-pad [a b]
  [(concat a [\u0000])
   (concat b [\u0000])])

(defn natural-compare
  "Compares two strings using natural, as opposed to lexical, sorting."
  ([a b] (natural-compare compare a b (cycle [non-digit digit]) (cycle [post-pad pre-pad])))
  ([cmp a b [pred & pred's] [pad & pad's]]
   (let [[a-take a-drop] (split-with pred a)
         [b-take b-drop] (split-with pred b)
         [a-take b-take] (pad a-take b-take)
         result (or (->> (map cmp a-take b-take) (drop-while zero?) first) 0)]
     (cond
       (and (empty? a-take) (empty? b-take)) 0
       (empty? a-take)                       +1
       (empty? b-take)                       -1
       (zero? result)                        (recur cmp a-drop b-drop pred's pad's)
       :else                                 result))))
