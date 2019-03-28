(ns simple-reframe.navigation.views
  (:require
    [re-frame.core :as rf]
    ))


(rf/reg-event-db
  :set-current-page
  (fn [db [_ new-current-page]]
    (assoc db :current-page new-current-page)))

(rf/reg-sub
  :current-page
  (fn [db _]
    (:current-page db)))


(defn navigation-panel
  [current-page]
  (let [[page] @current-page]
    [:div
     (when page
       [page])]))


(defn main-page []
  [navigation-panel (rf/subscribe [:current-page])])