(ns lytek.core
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [lytek.spec :as ls]))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World! Yay! Whohoo! Yeah! Boyeah!"))

(def attributes ls/attribute-keys)
(def abilities ls/ability-keys)