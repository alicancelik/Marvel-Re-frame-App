(ns simple-reframe.character.subs
    (:require [reagent.core :as reagent]
      [re-frame.core :as rf]
      [simple-reframe.db :refer [app-state]]
      [clojure.string :as str]))

(rf/reg-sub
  :active-button
  (fn [db _]
    (:active-button db)))

(rf/reg-sub
  :modal-visibility
  (fn [db _]
    (:modal-visibility db)))
