(ns simple-reframe.routes
  (:require-macros [secretary.core :refer [defroute]])
  (:import goog.history.Html5History)
  (:require [secretary.core :as secretary]
            [re-frame.core :refer [dispatch]]
            [goog.events :as events]
            [goog.history.EventType :as EventType]
            [reagent.core :as reagent]
            [simple-reframe.db :refer [app-state]]
            [simple-reframe.home.views :refer [home]]
            [simple-reframe.character.views :refer [character]]
            ))


(defn hook-browser-navigation! []
  (doto (Html5History.)
    (events/listen
      EventType/NAVIGATE
      (fn [event]
        (secretary/dispatch! (.-token event))))
    (.setEnabled true)))


(defn app-routes []
  (secretary/set-config! :prefix "#")


  (defroute "/" []
            (dispatch [:set-current-page [home :home]]))


  (defroute "/character" [query]
            (dispatch [:set-current-page [character :character]]))


  (hook-browser-navigation!))