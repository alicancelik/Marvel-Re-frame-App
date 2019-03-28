(ns ^:figwheel-no-load simple-reframe.dev
  (:require
    [simple-reframe.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
