(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]
            [clojure.data.priority-map :refer :all]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))


; My third clojure program.  Had to use some breakpoints to debug via a special macro.
; Also, looking at other solutions, there are much more concise ways to write the 'dist' function
(defn dist [word1 word2 & {:keys [n] :or {n 0}}]
  ; Assumes lengths of word1 and word2 are the same
  (let [len1 (count word1)
        len2 (count word2)]
    (if (= 0 len1) n
      (if (= 0 len2) n
        (if (or (= 1 len1) (= 1 len2))
          (if (= word1 word2)
            n
            (+ n 1))
          (let [c1 (subs word1 0 1)
                c2 (subs word2 0 1)
                r1 (subs word1 1)
                r2 (subs word2 1)]
            (if (= c1 c2)
              (dist r1 r2 :n n)
              (dist r1 r2 :n (+ n 1)))))))))



(defn futureCost [goal]
  (reduce (fn [pm word]
            (assoc pm (keyword word) (dist word goal)))
          (priority-map)
          (filter #(= (count %) (count goal)) words)))


(defn doublets [word1 goal]
  (loop [result [word1]
         current-word word1
         cand-words (remove #(= word1 %) (map name (keys (futureCost goal))))]
    (if (= current-word goal)
      result
      (let [w (first (filter
                      #(= 1 (dist % current-word))
                      cand-words))]
        (if (= nil w)
          []
          (recur (conj result w)
                 w
                 (remove #(= % w) cand-words)))))))
