(ns lytek.test-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [lytek.core-test]))

(doo-tests 'lytek.core-test)
