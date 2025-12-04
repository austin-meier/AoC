(ns y2025.day1
  (:require [common :refer get-input-lines]))

(def test-input
  ["L68"
   "L30"
   "R48"
   "L5"
   "R60"
   "L55"
   "L1"
   "L99"
   "R14"
   "L82"])

(def input (get-input-lines 2025 1))

(defn degrees [s]
  (case (first s)
    \L  (- (Integer/parseInt (subs s 1)))
    \R  (Integer/parseInt (subs s 1))))

(defn pass-zero? [a b]
  (or (and (neg? a) (pos? b))
      (and (pos? a) (neg? b))))

(->> input
 (reduce
  (fn [acc x]
    (let [deg (degrees x)
          new-deg (+ (:deg acc) deg)
          during (abs (quot new-deg 100))
          normalized (if (zero? new-deg)
                       new-deg
                       (rem new-deg 100))]
      {:deg normalized
       :end (if (zero? normalized)
              (+ (:end acc) 1)
              (:end acc))
       :during (+ (:during acc) (if (pass-zero? (:deg acc) new-deg) (inc during) during))}))
  {:deg 50 :end 0 :during 0}))