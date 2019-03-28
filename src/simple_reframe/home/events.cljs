(ns simple-reframe.home.events
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [simple-reframe.home.subs]
            [simple-reframe.common.subs]
            [simple-reframe.utils :as utils]
            [simple-reframe.db :refer [app-state]]
            [day8.re-frame.http-fx]
            [clojure.string :as str]))


(rf/reg-event-db
  :characters-change
  (fn [db [_ new-character]]
    (assoc db :characters new-character)))

(rf/reg-event-db
  :current-character-change
  (fn [db [_ new-current-champ]]
    (assoc db :current-character new-current-champ)))


(rf/reg-event-db
  :name-change
  (fn [db [_ new-name-change]]
    (assoc-in db [:params :name] new-name-change)))


(rf/reg-event-db
  :set-db
  (fn [db [_ params]]
    (assoc db :params params)
    ))


(rf/reg-event-db
  :remove-name
  (fn [db [_]]
    (update-in db [:params] dissoc :name)
    ))


(rf/reg-event-fx
:get-characters
(fn [{:keys [db]} _]
  (println db)
  (let [
        uri "/characters"]
    {:http-xhrio (utils/create-request-map :get uri
                                           :get-characters-ok
                                           :params (:params db)
                                           )})))

(rf/reg-event-fx
  :get-characters-ok
  (fn [{:keys [db]} [_ response]]
    {:db (assoc db :characters (-> response :data :results))}))