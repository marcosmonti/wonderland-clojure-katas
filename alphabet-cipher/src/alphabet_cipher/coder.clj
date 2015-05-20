(ns alphabet-cipher.coder)

; my first clojure program.
; hopefully i will get more comfortable using 'map' instead of 'for' in order to loop
; i was stuck on 'for' because my first idea was to use an equation that uses an index


(defn char-int [c]
  (- (int c) 97))
(defn int-char [i]
  (char (+ i 97)))

; encoded[i] = (keyword[i % keyword-length] + message[i]) % 26
(defn encode [keyword message]
  (apply str (for [i (range 0 (count message))
        :let [m (int-char (mod (+ (char-int (nth keyword (mod i (count keyword))))
                                  (char-int (nth message i)))
                               26))]]
    m)))

; decoded[i] = (message[i] - keyword[i % keyword-length]) % 26
(defn decode [keyword message]
  (apply str (for [i (range 0 (count message))
        :let [m (int-char (mod (- (char-int (nth message i))
                                  (char-int (nth keyword (mod i (count keyword)))))

                               26))]]
    m)))

