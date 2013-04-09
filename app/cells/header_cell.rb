class HeaderCell < Cell::Rails
  def show(args = {})
    @current_resource = args[:current_resource]
    if @current_resource == nil
      render view: :index
    else
      render view: :admin
    end
  end
end
