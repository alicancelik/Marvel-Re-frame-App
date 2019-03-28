(ns simple-reframe.character.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [simple-reframe.home.subs]
            [simple-reframe.common.subs]
            [simple-reframe.character.subs]
            [antizer.reagent :as ant]
            [simple-reframe.db :refer [app-state]]
            [clojure.string :as str]))


(def columns (reagent/atom [{:title "Name" :dataIndex "name" :key "name" }]))

(defn render-info []
  (fn []
    [:div {:style {:justify-content "center" :text-align "center" :color "white" :font-size 35}} (str (:name @(rf/subscribe [:current-character])))]
    ))


(defn render-avatar []
  (fn []
    [:img {:style {:width 200 :height 300 :border-radius 20} :src (str (-> @(rf/subscribe [:current-character]) :thumbnail :path) "/portrait_xlarge.jpg")}]))


(defn render-table []
  (let [events (vec (-> @(rf/subscribe [:current-character]) :events :items))
        stories (vec (-> @(rf/subscribe [:current-character]) :stories :items))
        comics (vec (->  @(rf/subscribe [:current-character]) :comics :items))]
    (fn []
      [:div {:style {:width 500
                        :justify-content "center"
                        :text-align "center"
                        :align-self "center"
                        :margin-left 450
                        :background-color "white"
                        :margin-top 50}}
      [ant/table {:dataSource (when-not (str/blank? @(rf/subscribe [:active-button]))
                              (case @(rf/subscribe [:active-button])
                                "events" events
                                "stories" stories
                                "comics" comics
                                ))
                  :pagination true
                  :columns @columns
                  }]]
    )))


(defn render-button-group []
  [:div { :style {:margin-top 40}}
   (let [active-button @(rf/subscribe [:active-button])]
  [ant/button-group
   [ant/button {:type (when (= active-button "events") "primary") :on-click #(rf/dispatch [:active-button-change "events"])} "Events"]
   [ant/button {:type (when (= active-button "stories") "primary") :on-click #(rf/dispatch [:active-button-change "stories"])} "Stories"]
   [ant/button {:type (when (= active-button "comics") "primary") :on-click #(rf/dispatch [:active-button-change "comics"])} "Comics"]])])


(defn render-modal-button []
  [ant/button {:on-click #(rf/dispatch [:change-modal-visibility true])
               :style {:justify-content "flex-end"
                       :float "right"
                       :margin-right "20%"}} "Show Detail"])

(defn render-modal []
  (fn []
    [ant/modal {
                :visible @(rf/subscribe [:modal-visibility])
                :title "Details"
                :on-ok #(rf/dispatch [:change-modal-visibility false])
                :on-cancel #(rf/dispatch [:change-modal-visibility false])
                }
      [:div {:style {:flex-wrap "wrap"}}
       [:div {:style {:display "flex"}}
        [:p "Name: "]
        [:p {:style {:font-weight "bold" :margin-left 10}} (str (:name @(rf/subscribe [:current-character])))]]
      ]
     [:div
      [:div {:style {:display "flex"}}
       [:p "Description: "]
       [:p {:style {:font-weight "bold" :margin-left 10}} (str (:description @(rf/subscribe [:current-character])))]]
      ]
     [:div
      [:div {:style {:display "flex"}}
       [:p "Count Of Comics: "]
       [:p {:style {:font-weight "bold" :margin-left 10}} (str (-> @(rf/subscribe [:current-character]) :comics :available))]]
      ]
     [:div
      [:div {:style {:display "flex"}}
       [:p "Count Of Events: "]
       [:p {:style {:font-weight "bold" :margin-left 10}} (str (-> @(rf/subscribe [:current-character]) :events :available))]]
      ]
     [:div
      [:div {:style {:display "flex"}}
       [:p "Count Of Series: "]
       [:p {:style {:font-weight "bold" :margin-left 10}} (str (-> @(rf/subscribe [:current-character]) :series :available))]]
      ]
     [:div
      [:div {:style {:display "flex"}}
       [:p "Count Of Stories: "]
       [:p {:style {:font-weight "bold" :margin-left 10}} (str (-> @(rf/subscribe [:current-character]) :stories :available))]]
      ]
     ]
    )
  )


(defn character []
  (fn []
    (reagent/create-class
      {:component-did-mount (do
                              #(rf/dispatch [:change-modal-visibility false])
                              #(rf/dispatch [:active-button-change "events"])
                              )
       :reagent-render (fn []
                         [:div {:style {:display "grid"}}
                          [:div {:style {:margin-top 40}}
                            [render-modal-button]]
                          [:div {:style {:justify-content "center" :text-align "center" :margin-top 40}}
                            [render-info]
                            [render-avatar]
                            [:div {:style {:margin-bottom 50}}
                              [render-button-group]
                              [render-modal]
                              [render-table]]]])})))
