(ns simple-reframe.character.events
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [simple-reframe.home.subs]
            [simple-reframe.common.subs]
            [simple-reframe.character.subs]
            [simple-reframe.utils :as utils]
            [simple-reframe.db :refer [app-state]]
            [day8.re-frame.http-fx]
            [clojure.string :as str]))


(rf/reg-event-db
  :active-button-change
  (fn [db [_ new-active-button]]
    (assoc db :active-button new-active-button)))

(rf/reg-event-db
  :change-modal-visibility
  (fn [db [_ new-visibility-value]]
    (assoc db :modal-visibility new-visibility-value)))

