(ns lytek.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]
            [clojure.set :as se]))


(def attribute-keys
  [:strength
   :dexterity
   :stamina
   :charisma
   :manipulation
   :appearance
   :perception
   :intelligence
   :wits])

(def ability-keys
  [:archery
   :athletics
   :awareness
   :brawl
   :bureaucracy
   :craft
   :dodge
   :integrity
   :investigation
   :larceny
   :linguistics
   :lore
   :martial-arts
   :medicine
   :melee
   :occult
   :performance
   :presence
   :resistance
   :ride
   :sail
   :socialize
   :stealth
   :survival
   :thrown
   :war])


(s/def :lytek/name
  string?)

(s/def :lytek/owner
  string?)

(s/def :lytek/category
  #{:character
    :rulebook})

(def solar-castes
  #{:dawn
    :night
    :eclipse
    :twilight
    :zenith})  

(def terrestrial-aspects
  #{:wood
    :air
    :water
    :fire
    :earth})

(s/def :lytek/character-type
  (se/union solar-castes
            terrestrial-aspects))      

    


(s/def :lytek/rulebooks
  (s/coll-of string? :into [] :distinct true))

(s/def :lytek/attribute
  (set attribute-keys))
(s/def :lytek/attributes
  (s/map-of :lytek/attribute (s/int-in 0 6)))

(s/def :lytek/ability
  (set ability-keys))
(s/def :lytek/abilities
  (s/and
    (s/map-of :lytek/ability (s/int-in 0 6))
    #(not (or (:craft %) (:martial-arts %)))))

(s/def :lytek/supernal
  :lytek/ability)
(s/def :lytek/favored-abilities
  (s/coll-of :lytek/ability :count 10 :into #{}))  

(s/def :lytek/charms
  (s/coll-of string? :into []))

(s/def :lytek/anima
  string?)

(s/def :lytek/health-level
  (s/int-in 0 51))
(s/def :lytek/health-levels
  (s/tuple :lytek/health-level :lytek/health-level :lytek/health-level :lytek/health-level))
(s/def :lytek/damage-bashing
  pos-int?)
(s/def :lytek/damage-lethal
  pos-int?)
(s/def :lytek/damage-aggravated
  pos-int?)
(s/def :lytek/healthy
  (s/keys :req-un [:lytek/health-levels
                   :lytek/damage-bashing
                   :lytek/damage-lethal
                   :lytek/damage-aggravated]))


(s/def :lytek/willpower-temporary (s/int-in 0 11))
(s/def :lytek/willpower-maximum (s/int-in 0 11))

(s/def :lytek/limit-trigger string?)
(s/def :lytek/limit-accrued (s/int-in 0 11))

(s/def :lytek/entity
  (s/keys :req-un [:lytek/category
                   :lytek/name
                   :lytek/owner]))

(s/def :lytek/combatant
  (s/merge :lytek/entity
           :lytek/healthy
           (s/keys :req-un [:lytek/attributes
                            :lytek/abilities
                            :lytek/willpower-maximum
                            :lytek/willpower-temporary])))


(s/def :lytek/character
  (s/and
    (s/merge :lytek/entity
             :lytek/combatant
             (s/keys :req-un [:lytek/character-type
                              :lytek/anima
                              :lytek/rulebooks
                              :lytek/charms]))
             
    #(= (:category %) :character)))

(s/def :lytek/solar
  (s/and
    (s/merge :lytek/character
            (s/keys :req-un [:lytek/limit-trigger
                             :lytek/limit-accrued
                             :lytek/supernal
                             :lytek/favored-abilities]))
    #(s/valid? solar-castes (:character-type %))
    #(s/valid? (set (:lytek/favored-abilities %)) (:supernal %))))
