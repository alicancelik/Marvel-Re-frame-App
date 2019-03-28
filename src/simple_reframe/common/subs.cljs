(ns simple-reframe.common.subs
(:require [reagent.core :as reagent]
  [re-frame.core :as rf]
  [simple-reframe.db :refer [app-state]]
  [clojure.string :as str]))


(rf/reg-sub
  :current-character
  (fn [db _]
    (:current-character db)))