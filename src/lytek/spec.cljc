(ns lytek.spec
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.gen.alpha :as gen]))


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

(s/def :lytek/character-type
  #{:dawn
    :night
    :eclipse
    :twilight
    :zenith

    :wood
    :air
    :water
    :fire
    :earth})


(s/def :lytek/rulebooks
  (s/coll-of string? :into [] :distinct true))

(s/def :lytek/attribute
  (set attribute-keys))
(s/def :lytek/attributes
  (s/map-of :lytek/attribute (s/int-in 0 5)))

(s/def :lytek/ability
  (set ability-keys))
(s/def :lytek/abilities
  (s/and
    (s/map-of :lytek/ability (s/int-in 0 5))
    #(not (or (:craft %) (:martial-arts %)))))

(s/def :lytek/charms
  (s/coll-of string? :into []))

(s/def :lytek/anima
  string?)


(s/def :lytek.health/levels
  (s/tuple pos-int? pos-int? pos-int? pos-int?))
(s/def :lytek.health/bashing
  pos-int?)
(s/def :lytek.health.damage/bashing
  pos-int?)
(s/def :lytek.health.damage/lethal
  pos-int?)
(s/def :lytek.health.damage/aggravated
  pos-int?)
(s/def :lytek/health
  (s/keys :req-un [:lytek.health/levels
                   :lytek.health.damage/bashing
                   :lytek.health.damage/lethal
                   :lytek.health.damage/aggravated]))


(s/def :lytek/entity
  (s/keys :req-un [:lytek/category
                   :lytek/name
                   :lytek/owner]))

(s/def :lytek/combatant
  (s/merge :lytek/entity
           (s/keys :req-un [:lytek/attributes
                            :lytek/abilities
                            :lytek/health])))

(s/def :lytek/character
  (s/and
    (s/merge :lytek/entity
             :lytek/combatant
             (s/keys :req-un [:lytek/character-type
                              :lytek/anima
                              :lytek/rulebooks
                              :lytek/charms]))
    #(= (:category %) :character)))
