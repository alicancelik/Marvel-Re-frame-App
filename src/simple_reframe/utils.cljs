(ns simple-reframe.utils
  (:require [ajax.core :as ajax]))


(defonce api-url "http://gateway.marvel.com/v1/public")

(defn create-request-map
  ([type uri on-success params]
   (create-request-map type uri on-success params nil))
  ([type uri on-success on-fail params]
   (cond-> {
            :method          type
            :uri             (str api-url uri)
            :params           params
            :format          (ajax/json-request-format)
            :response-format (ajax/json-response-format {:keywords? true})
            :on-success      (if (vector? on-success) on-success [on-success])
            :on-failure      (if (vector? on-fail) on-fail [on-fail])}
           (nil? on-fail) (dissoc :on-failure))))