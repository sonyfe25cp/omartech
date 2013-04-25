class Settings < SettingsLogic
  source "#{Rails.root}/config/omartech.yml"
  namespace Rails.env
end
