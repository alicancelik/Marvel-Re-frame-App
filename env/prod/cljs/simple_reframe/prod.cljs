(ns simple-reframe.prod
  (:require
    [simple-reframe.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
