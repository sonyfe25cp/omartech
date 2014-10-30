class ApplicationController < ActionController::Base
  protect_from_forgery

  protected
  def after_sign_in_path_for(resource)
    if resource.is_a? Admin::Admin
      return admin_orders_url
    end
    stored_location_for(resource) || root_url
  end

  def current_resource
    send("current_#{current_resource_name}")
  end
  def current_resource_layout
   "#{current_resource_name}/default"
  end

  def current_resource_name

  end
end
