(ns simple-reframe.home.views
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [simple-reframe.db :refer [app-state]]
            [simple-reframe.home.subs]
            [simple-reframe.common.subs]
            [simple-reframe.character.subs]
            [antizer.reagent :as ant]
            [clojure.string :as str]))



(defn render-champions-name []
  (fn []
    (let [characters @(rf/subscribe [:characters])]
      [:div {:style {:display         "flex"
                     :flex-wrap       "wrap"
                     :text-align      "center"
                     :justify-content "center"
                     :padding-left    220
                     :padding-right   220
                     }}
       (if characters
         (for [character characters]
           ^{:key (str (random-uuid))}
           [:div {:style {:margin 5 :flex-basis "25%"}}
            [:a {:on-click #(rf/dispatch [:current-character-change character])
                 :href     (str "/#character?id=" (:id character))
                 }
             [:img {:style {:width 200 :height 300 :border-radius 20} :src (str (-> character :thumbnail :path) "/portrait_xlarge.jpg")}]]
            [:p {:style {:color "white"}} (:name character)]]
           )
         [:img {:src "https://media.giphy.com/media/PBln5fAX5FCpO/giphy.gif" :width 500 :height 500}]
         )])))

(defn load-more []
  (fn []
    (swap! app-state assoc-in [:params :limit] (+ 30 (-> @app-state :params :limit)))
    (rf/dispatch [:get-characters])))

(defn render-load-more []
  (fn []
    (let [characters @(rf/subscribe [:characters])]
      (if characters
        [ant/button {:on-click (load-more) :style {:width 220 :margin-bottom 20 :color "white" :background-color "black"}} "Load More"]
        ))))


(defn search-fn []
  (if (str/blank? @(rf/subscribe [:name]))
    (do
      (rf/dispatch [:remove-name ])
      (rf/dispatch [:get-characters]))
    (do
      (swap! app-state assoc-in [:params :name] @(rf/subscribe [:name]))
      (rf/dispatch [:get-characters]))
    ))


(defn render-search []
  (fn []
    [:div {:style {:margin-top 10
                   :position   "absolute"
                   :right      250}}
     [:input {:type        "text"
              :style       {:width 200 :border-radius 5}
              :placeholder "   Search ..."
              :value       @(rf/subscribe [:name])
              :on-change   #(rf/dispatch [:name-change (-> % .-target .-value)])
              }
      ]
     [ant/button {:on-click #(search-fn)
                  :style    {:color            "white"
                             :background-color "black"
                             :height           27}} "Search"]]))


(defn home []
  (fn []
    (reagent/create-class
      {:component-did-mount (do
                              (rf/dispatch [:change-modal-visibility false])
                              (rf/dispatch [:set-db (:params @app-state)])
                              #(rf/dispatch [:active-button-change "events"])
                              #(rf/dispatch [:get-characters]))
       :reagent-render      (fn []
                              [:div {:style {:justify-content "center" :text-align "center"}}
                               [:div {:style {:display "inline-flex" :flex-direction "row"}}
                                [:img {:src "https://wordofthenerdonline.com/wp-content/uploads/2018/10/02/Marvel-Comics-logo-red-on-black.png" :style {:width 300 :height 100}}]
                                [render-search]
                                ]
                               [:div
                                [render-champions-name]
                                [render-load-more]
                                ]
                               ])
       })))