# -*- encoding: utf-8 -*-
class CasesController < BaseController
  def index
    @cases = Case.all

    respond_to do |format|
      format.html
      format.json {render json:  @cases}
    end
  end
end
