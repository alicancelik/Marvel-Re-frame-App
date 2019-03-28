(ns simple-reframe.home.subs
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [simple-reframe.db :refer [app-state]]
            [clojure.string :as str]))

(rf/reg-sub
  :characters
  (fn [db _]
    (:characters db)))


(rf/reg-sub
  :name
  (fn [db _]
    (-> db :params :name)))

