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
  (s/and string?
         #(not (empty? %))))

(s/def :lytek/title
  string?)

(s/def :lytek/background
  string?)  

(s/def :lytek/description
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

(s/def :lytek/rank  
  (s/int-in 0 6))

(s/def :lytek/rulebooks
  (s/coll-of :lytek/name :into [] :distinct true))

(s/def :lytek/attribute
  (set attribute-keys))
(s/def :lytek/attributes
  (s/map-of :lytek/attribute (s/int-in 1 6)))

(s/def :lytek/ability
  (set ability-keys))
(s/def :lytek/abilities
  (s/and
    (s/map-of :lytek/ability :lytek/rank)
    #(not (or (:craft %) (:martial-arts %)))))

(s/def :lytek/additional-ability
  (s/tuple :lytek/ability
           :lytek/rank
           :lytek/description))                    
(s/def :lytek/additional-abilities
  (s/coll-of :lytek/additional-ability))  

(s/def :lytek/supernal
  :lytek/ability)
(s/def :lytek/favored-abilities
  (s/coll-of :lytek/ability :count 10 :into #{}))

(s/def :lytek/specialty
  (s/tuple :lytek/ability
           :lytek/description))
(s/def :lytek/specialties
  (s/coll-of :lytek/specialty))  

(s/def :lytek/charms
  (s/coll-of :lytek/name :into []))

(s/def :lytek/anima
  string?)

(s/def :lytek/health-boxes
  (s/int-in 0 51))
(s/def :lytek/health-levels
  (s/coll-of :lytek/health-boxes :count 4 :into []))
(s/def :lytek/damage-bashing
  :lytek/health-boxes)
(s/def :lytek/damage-lethal
  :lytek/health-boxes)
(s/def :lytek/damage-aggravated
  :lytek/health-boxes)
(s/def :lytek/healthy
  (s/keys :req-un [:lytek/health-levels
                   :lytek/damage-bashing
                   :lytek/damage-lethal
                   :lytek/damage-aggravated]))

(s/def :lytek/willpower-temporary (s/int-in 0 11))
(s/def :lytek/willpower-maximum (s/int-in 0 11))

(s/def :lytek/limit-trigger string?)
(s/def :lytek/limit-accrued (s/int-in 0 11))

(s/def :lytek/essence-rating
  (s/int-in 1 6))

(s/def :lytek/motepool
  (s/int-in 0 200))
(s/def :lytek/essence-personal
  :lytek/motepool)         
(s/def :lytek/essence-peripheral
  :lytek/motepool)
(s/def :lytek/committed-personal
  :lytek/motepool)
(s/def :lytek/committed-peripheral
  :lytek/motepool)

(s/def :lytek/entity
  (s/keys :req-un [:lytek/category
                   :lytek/name
                   :lytek/owner]))

(s/def :lytek/combatant
  (s/merge :lytek/entity
           :lytek/healthy
           (s/keys :req-un [:lytek/attributes
                            :lytek/abilities
                            :lytek/additional-abilities
                            :lytek/willpower-maximum
                            :lytek/willpower-temporary])))

(s/def :lytek/character
  (s/and
    (s/merge :lytek/entity
             :lytek/combatant
             (s/keys :req-un [:lytek/character-type
                              :lytek/anima
                              :lytek/rulebooks
                              :lytek/charms]
                      :opt-un [:lytek/background
                               :lytek/title]))
             
    #(= (:category %) :character)))

(s/def :lytek/enlightened
  (s/merge :lytek/character
           (s/keys :req-un [:lytek/essence-rating
                            :lytek/essence-personal
                            :lytek/essence-peripheral
                            :lytek/committed-personal
                            :lytek/committed-peripheral])))

(s/def :lytek/solar
  (s/and
    (s/merge :lytek/enlightened
            (s/keys :req-un [:lytek/limit-trigger
                             :lytek/limit-accrued
                             :lytek/supernal
                             :lytek/favored-abilities]))
    #(contains? solar-castes (:character-type %))))
