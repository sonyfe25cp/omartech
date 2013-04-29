# -*- encoding: utf-8 -*-
class Admin::PostsController < Admin::BaseController

  def index
    @posts = Post.all(:order => [:id.desc]).page(params[:page]).limit(params[:limit] || 10)
    respond_to do |format|
      format.html
    end
  end

  def show

    @post = Post.get(params[:id])

    respond_to do |format|
      format.html
      format.json {render json: @post}
    end

  end

  def new
    @post = Post.new

  end

  def create

    post = Post.new(params[:post])
    post.save

    redirect_to admin_posts_path

  end

  def edit

    @post = Post.get(params[:id])

  end

  def update
    post = Post.get(params[:id])
    post.update(:title => params[:post][:title], :content => params[:post][:content], :status => params[:post][:status].to_sym)
    redirect_to admin_posts_path
  end

  def destroy

    Post.get(params[:id]).destroy
    redirect_to admin_posts_path
  end

end
