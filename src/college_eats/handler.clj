(ns college-eats.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [college-eats.view :refer :all]
            [college-eats.core :refer :all]
            [ring.util.response :refer [redirect]]
            ))
(defn parse-int  [s]
     (Integer.  (re-find  #"\d+" s )))

(defn show-recipe-handler
  [req]
  (let
    [recipe-id (-> req :params :id parse-int)]
    (recipe->html (get-recipe recipe-id))))

(defn show-recipe-list-handler
  [req]
  (recipe-list->html (get-recipes)))

(defn show-home-handler
  [req]
  (home->html))

(defn random-recipe-handler
  [req]
  (random-recipe->html (get-recipes)))
(defn add-recipe-form-handler
  [req]
  (recipe-form {:id  (generate-recipe-id)}))
(defn edit-recipe-form-handler
  [req]
  (let
    [recipe-id (-> req :params :id parse-int)] 
    (recipe-form (get-recipe recipe-id))))
(defn commit-edit-recipe-handler
  [req]
  (let
    [id (-> req :params :id parse-int) name (-> req :params :name) ingredients (-> req :params :ingredients) 
     directions (-> req :params :directions) cook-time (-> req :params :cook-time) difficulty (-> req :params :difficulty) action (-> req :params :action)]
    (if (= action "Cancel")
      (redirect "/recipe") 
          (do
            (db-add-recipe!
           (create-recipe
             id
             name
             ingredients
             directions
             cook-time
             difficulty
             ))
           (redirect "/recipe")
           )
           )
 )) 


(defn delete-recipe-handler
  [req]
      (db-remove-recipe! (-> req :params :id parse-int))
      (redirect "/recipe")
  )
(defroutes app-routes
  (GET "/recipe/:id" [] show-recipe-handler)
  (GET "/recipe" [] show-recipe-list-handler)
  (GET "/random" [] random-recipe-handler)
  (GET "/" [] show-home-handler)
  (GET "/add_recipe" [] add-recipe-form-handler)
  (GET "/edit_recipe/:id" [] edit-recipe-form-handler)
  (POST "/commit_edit_recipe" [] commit-edit-recipe-handler)
  (GET "/delete_recipe/:id" [] delete-recipe-handler)
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
