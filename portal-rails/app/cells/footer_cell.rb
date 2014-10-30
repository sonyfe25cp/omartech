class FooterCell < Cell::Rails
  def show(args = {})
    render view: args[:scope] || :user
  end
end
