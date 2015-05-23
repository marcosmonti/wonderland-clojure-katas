(ns card-game-war.game)

;; My second clojure program.

(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(defn play-round [player1-card player2-card]
  (let [res (- (.indexOf ranks (last player1-card))
               (.indexOf ranks (last player2-card)))]
    (if (= res 0)
      (- (.indexOf suits (first player1-card))
         (.indexOf suits (first player2-card)))
      res)
))

(defn play-game [player1-cards player2-cards]
  (if (empty? player1-cards) -1
    (if (empty? player2-cards) 1
  (let [res (play-round (first player1-cards) (first player2-cards))]
    (if (> res 0)
      (play-game (conj (conj (subvec player1-cards 1)
                             (first player1-cards))
                       (first player2-cards))
                 (subvec player2-cards 1))
      (play-game (subvec player1-cards 1)
                 (conj (conj (subvec player2-cards 1)
                             (first player2-cards))
                       (first player1-cards))))))))
