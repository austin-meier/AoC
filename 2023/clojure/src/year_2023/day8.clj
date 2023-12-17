(ns year-2023.day8
  (:require [year-2023.common :refer [get-input]]))

(def test-input
  ["RL"
   "AAA = (BBB, CCC)"
   "BBB = (DDD, EEE)"
   "CCC = (ZZZ, GGG)"
   "DDD = (DDD, DDD)"
   "EEE = (EEE, EEE)"
   "GGG = (GGG, GGG)"
   "ZZZ = (ZZZ, ZZZ)"])


(def test-input2
  ["LR"
   "11A = (11B, XXX)"
   "11B = (XXX, 11Z)"
   "11Z = (11B, XXX)"
   "22A = (22B, XXX)"
   "22B = (22C, 22C)"
   "22C = (22Z, 22Z)"
   "22Z = (22B, 22B)"
   "XXX = (XXX, XXX)"])

(defn create-node [n]
  [(keyword (subs n 0 3)) {\L (keyword (subs n 7 10)) \R (keyword (subs n 12 15))}])

(defn build-nodes [data]
  (->> data
       (map create-node)
       (into {})))

(defn part1-reduce [input]
  (let [instructions (cycle (first input))
        data (filter not-empty (rest input))
        nodes (build-nodes data)]
    (reduce (fn [acc i]
              (let [new-node (get ((first acc) nodes) i)]
                (if (= :ZZZ new-node)
                  (reduced (last acc))
                  [new-node (inc (last acc))])))
            [:AAA 1] instructions)))


(defn part1 [input]
  (let [instructions (cycle (first input))
        data (filter not-empty (rest input))
        nodes (build-nodes data)]
    (loop [steps 1
           node :AAA
           eip instructions]
      (let [new-node (get (node nodes) (first eip))]
        (if (= :ZZZ new-node)
          steps
          (recur (inc steps) new-node (rest eip)))))))

#_(part1 test-input)
#_(part1 (get-input 8))

(defn gcd
      [a b]
      (if (zero? b)
      a
      (recur b, (mod a b))))

(defn lcm
      [a b]
      (/ (* a b) (gcd a b)))

;; Least common multiple for variadic agrs
(defn lcmv [& v] (reduce lcm v))


(defn part2 [input]
  (let [instructions (cycle (first input))
        data (filter not-empty (rest input))
        nodes (build-nodes data)
        starting-nodes (->> (keys nodes)
                            (filter #(= (last (name %)) \A)))]
    (->> (map (fn [start-node]
               (loop [steps 1
                      node start-node
                      eip instructions]
                 (let [new-node (get (node nodes) (first eip))]
                   (if (= \Z (last (name new-node)))
                     steps
                     (recur (inc steps) new-node (rest eip))))))
             starting-nodes)
         (apply lcmv))))

#_(part2 test-input2)
;; => 6

(part2 (get-input 8))
