(ns simple-reframe.core
  (:require [reagent.core :as r]
            [re-frame.core :as re-frame]
            [simple-reframe.navigation.views :as views]
            [simple-reframe.home.events]
            [simple-reframe.character.events]
            [simple-reframe.db :refer [app-state]]
            [simple-reframe.routes :refer [app-routes]]))


(defn mount-root []
  (r/render [views/main-page] (.getElementById js/document "app")))


(defn init!
  []
  (mount-root)
  (app-routes))