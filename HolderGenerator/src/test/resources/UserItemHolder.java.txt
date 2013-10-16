/**
* This is just a proof-of-concept: generation result should be like that
*/

public class UserItemHolder {

  //-- generated fields - do not modify
  
  /**
  * Original view.
  */
  private View mView;
  
  /**
  * Field for R.id.name.
  */
  private TextView mName;
  
  /**
  * Field for R.id.user_avatar.
  */
  private ImageView mUserAvatar;
  
  //-- end of generated fields
  
  //-- generated methods do not modify
  
  /**
  * Need to be called to initialise fields.
  */
  protected void init(View view) {
    mView = view;
    mName = (TextView)view.findViewById(R.id.name);
    mUserAvatar = (ImageView)view.findViewById(R.id.user_avatar);
    afterInit(view);
  }
  
  /**
  * Returns original view associated with this holder.
  */
  public View getView() {
    return mView;
  }
  
  /**
  * Creates holder from {@link LayoutInflater} with parent.
  * Parent can be null.
  */
  public static UserItemHolder create(LayoutInflater inflater, ViewParent parent) {
      View view = inflater.inflate(R.layout.user_item, parent, false);
      return create(view);
  }
  
  /**
  * Creates holder from view.
  */
  public static UserItemHolder create(View view) {
    UserItemHolder holder = new UserItemHolder();
    holder.init(view);
  }
  
  /**
  * Convention way to get holder from adapters.
  * Tries to retrieve holder associated with oldView.
  * If view is null, new view will be created with passed parent and associated with new holder.
  * If tag is null, new holder will be created and associated with view.
  */
  public static UserItemHolder get(View oldView, ViewParent parent) {
    if (oldView == null) {
      if (parent == null) {
        throw new NullPointerException("Parent can not be null");
      }
      LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      return create(inflater, parent);
    }
    Object holder = oldView.getTag();
    if (holder == null) {
      return create(oldView);
    }
    
    try {
      return (UserItemHolder)holder;
    } catch(ClassCastException ex){
      throw new IllegalArgumentException("View has tag that is not UserItemHolder", ex);
    }
  }
  
  //-- end of generated methods
  
  /**
  * Creates empty UserItemHolder.
  * It will be invalid until {@link #init(View)} will be called.
  */
  protected UserItemHolder(){
  }
  
  /**
  * Will be called at the end of {@link #init(View)}.
  */
  private void afterInit(View view) {
  }

}
