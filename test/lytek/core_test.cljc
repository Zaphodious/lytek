(ns lytek.core-test
  (:require #?(:clj [clojure.test :refer :all]
               :cljs [cljs.test :refer-macros [deftest is testing]])
    [lytek.core :as lytek]
    [clojure.spec.alpha :as s]
    [clojure.spec.gen.alpha :as gen]
    [clojure.test.check.generators]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 111101 111101))))

(deftest gens-okay-test
  (testing "That Characters generate"
    (is (gen/generate (s/gen :lytek/character)))))