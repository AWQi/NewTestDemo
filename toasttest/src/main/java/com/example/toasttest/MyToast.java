package com.example.toasttest;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.support.design.widget.BaseTransientBottomBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by admin on 2018/11/26.
 */

public class MyToast {
    private static final String TAG = "MyToast";


    static private  Toast toast;
//    static private MyToast myToast;
//   static private  Field field;
//   static private  Object obj;
   static private  Object mTN;
   static private  boolean isVisible = true;

    private MyToast(Context context,Toast toast) {

        this.toast = toast;
    }
//    @Override
//    public static Toast makeText(Context context, CharSequence text, @Duration int duration) {
//        return makeText(context, null, text, duration);
//    }

    static  Toast getIntance(Context context,String str) {
       //先创建一个Toast对象
//        myToast = new MyToast(context,Toast.makeText(context,str,Toast.LENGTH_LONG));

        toast = Toast.makeText(context,str,Toast.LENGTH_SHORT);
//       try {
//          Field field = toast.getClass().getDeclaredField("mTN");
//           field.setAccessible(true);
//           mTN = field.get(toast);
//       } catch (NoSuchFieldException e) {
//           e.printStackTrace();
//           Log.d(TAG, "getIntance: "+e.getMessage());
//       } catch (IllegalAccessException e) {
//           e.printStackTrace();
//           Log.d(TAG, "getIntance: "+e.getMessage());
//       }

       return  toast;
   }
//    private  void  getField(){
//        try {
//            field = toast.getClass().getDeclaredField("mTN");
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    private  Object getMTN(){
//       try {
//           field.setAccessible(true);
//           mTN = field.get(toast);
//       } catch (IllegalAccessException e) {
//           e.printStackTrace();
//       }
//    return  null;
//    }

   public void show(){

//           method.invoke(mTN,  new  byte[]{});

           new Thread(new Runnable() {
               @Override
               public void run() {
                   while(isVisible){
                       try {
                           //设置Toast信息提示框显示的位置（在屏幕顶部水平居中显示）
                           toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);

//从Toast对象中获得mTN变量
                               Field field=toast.getClass().getDeclaredField("mTN");
                               field.setAccessible(true);
                               final Object mTN =field.get(toast);
//TN对象中获得了show方法
                               final Method method = mTN.getClass().getDeclaredMethod("show", null);
//调用show方法来显示Toast信息提示框


                           method.invoke(mTN,  new  byte[]{});
                           Thread.sleep(3000);
                       } catch (IllegalAccessException e) {
                           e.printStackTrace();
                           Log.d(TAG, "run: "+e.getMessage());
                       } catch (InvocationTargetException e) {
                           e.printStackTrace();
                           Log.d(TAG, "run: "+e.getMessage());
                       } catch (InterruptedException e) {
                           e.printStackTrace();
                           Log.d(TAG, "run: "+e.getMessage());
                       } catch (NoSuchMethodException e) {
                           e.printStackTrace();
                           Log.d(TAG, "run: "+e.getMessage());
                       } catch (NoSuchFieldException e) {
                           e.printStackTrace();
                       }
                   }

               }
           }).start();


   }

    /**
     * 反射字段
     * @param object 要反射的对象
     * @param fieldName 要反射的字段名称
     */ private static Object getField(Object object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            if (field != null) {
                field.setAccessible(true);
                return field.get(object);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.d(TAG, "getField: "+e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.d(TAG, "getField: "+e.getMessage());
        }

        return null;
}

//   public  MyToast setClickable(){
//       if (mTN != null) {
//           Object mParams = getField(mTN, "mParams");
//           if (mParams != null && mParams instanceof WindowManager.LayoutParams) {
//               WindowManager.LayoutParams params = (WindowManager.LayoutParams) mParams;
//               //显示与隐藏动画
////               params.windowAnimations = R.style.ClickToast;
//               //Toast可点击
//               params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//               //设置viewgroup宽高
//               params.width = WindowManager.LayoutParams.MATCH_PARENT;
//               //设置Toast宽度为屏幕宽度
//               params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//               //设置高度
//               } }
//               return  myToast;
//           }
//
//
//   public  void hide(){
//       try
//       {
//           isVisible =false;
////需要将前面代码中的obj变量变成类变量。这样在多个地方就都可以访问了
//           Method method=mTN.getClass().getDeclaredMethod("hide",null);
//           method.invoke(mTN, new  byte[]{});
//       }
//       catch(Exception e)
//       {
//           Log.d(TAG, "hide: "+e.getMessage());
//       }
//   }






//
//   private  static  class  MyTN extends

//
//    private static NotificationManager sService;
//
//    static private NotificationManager getService() {
//        if (sService != null) {
//            return sService;
//        }
//        sService = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
//        return sService;
//    }
//
//
//
//
//
//    private static class TN extends ITransientNotification.Stub {
//        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
//
//        private static final int SHOW = 0;
//        private static final int HIDE = 1;
//        private static final int CANCEL = 2;
//        final Handler mHandler;
//
//        int mGravity;
//        int mX, mY;
//        float mHorizontalMargin;
//        float mVerticalMargin;
//
//
//        View mView;
//        View mNextView;
//        int mDuration;
//
//        WindowManager mWM;
//
//        String mPackageName;
//
//        static final long SHORT_DURATION_TIMEOUT = 4000;
//        static final long LONG_DURATION_TIMEOUT = 7000;
//
//        TN(String packageName, @Nullable Looper looper) {
//            // XXX This should be changed to use a Dialog, with a Theme.Toast
//            // defined that sets up the layout params appropriately.
//            final WindowManager.LayoutParams params = mParams;
//            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
//            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
//            params.format = PixelFormat.TRANSLUCENT;
//            params.windowAnimations = com.android.internal.R.style.Animation_Toast;
//            params.type = WindowManager.LayoutParams.TYPE_TOAST;
//            params.setTitle("Toast");
//            params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
//
//            mPackageName = packageName;
//
//            if (looper == null) {
//                // Use Looper.myLooper() if looper is not specified.
//                looper = Looper.myLooper();
//                if (looper == null) {
//                    throw new RuntimeException(
//                            "Can't toast on a thread that has not called Looper.prepare()");
//                }
//            }
//            mHandler = new Handler(looper, null) {
//                @Override
//                public void handleMessage(Message msg) {
//                    switch (msg.what) {
//                        case SHOW: {
//                            IBinder token = (IBinder) msg.obj;
//                            handleShow(token);
//                            break;
//                        }
//                        case HIDE: {
//                            handleHide();
//                            // Don't do this in handleHide() because it is also invoked by
//                            // handleShow()
//                            mNextView = null;
//                            break;
//                        }
//                        case CANCEL: {
//                            handleHide();
//                            // Don't do this in handleHide() because it is also invoked by
//                            // handleShow()
//                            mNextView = null;
//                            try {
//                                getService().cancelToast(mPackageName, TN.this);
//                            } catch (RemoteException e) {
//                            }
//                            break;
//                        }
//                    }
//                }
//            };
//        }
//
//        /**
//         * schedule handleShow into the right thread
//         */
//        @Override
//        public void show(IBinder windowToken) {
//            if (localLOGV) Log.v(TAG, "SHOW: " + this);
//            mHandler.obtainMessage(SHOW, windowToken).sendToTarget();
//        }
//
//        /**
//         * schedule handleHide into the right thread
//         */
//        @Override
//        public void hide() {
//            if (localLOGV) Log.v(TAG, "HIDE: " + this);
//            mHandler.obtainMessage(HIDE).sendToTarget();
//        }
//
//        public void cancel() {
//            if (localLOGV) Log.v(TAG, "CANCEL: " + this);
//            mHandler.obtainMessage(CANCEL).sendToTarget();
//        }
//
//        public void handleShow(IBinder windowToken) {
//            if (localLOGV) Log.v(TAG, "HANDLE SHOW: " + this + " mView=" + mView
//                    + " mNextView=" + mNextView);
//            // If a cancel/hide is pending - no need to show - at this point
//            // the window token is already invalid and no need to do any work.
//            if (mHandler.hasMessages(CANCEL) || mHandler.hasMessages(HIDE)) {
//                return;
//            }
//            if (mView != mNextView) {
//                // remove the old view if necessary
//                handleHide();
//                mView = mNextView;
//                Context context = mView.getContext().getApplicationContext();
//                String packageName = mView.getContext().getOpPackageName();
//                if (context == null) {
//                    context = mView.getContext();
//                }
//                mWM = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//                // We can resolve the Gravity here by using the Locale for getting
//                // the layout direction
//                final Configuration config = mView.getContext().getResources().getConfiguration();
//                final int gravity = Gravity.getAbsoluteGravity(mGravity, config.getLayoutDirection());
//                mParams.gravity = gravity;
//                if ((gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
//                    mParams.horizontalWeight = 1.0f;
//                }
//                if ((gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
//                    mParams.verticalWeight = 1.0f;
//                }
//                mParams.x = mX;
//                mParams.y = mY;
//                mParams.verticalMargin = mVerticalMargin;
//                mParams.horizontalMargin = mHorizontalMargin;
//                mParams.packageName = packageName;
//                mParams.hideTimeoutMilliseconds = mDuration ==
//                        Toast.LENGTH_LONG ? LONG_DURATION_TIMEOUT : SHORT_DURATION_TIMEOUT;
//                mParams.token = windowToken;
//                if (mView.getParent() != null) {
//                    if (localLOGV) Log.v(TAG, "REMOVE! " + mView + " in " + this);
//                    mWM.removeView(mView);
//                }
//                if (localLOGV) Log.v(TAG, "ADD! " + mView + " in " + this);
//                // Since the notification manager service cancels the token right
//                // after it notifies us to cancel the toast there is an inherent
//                // race and we may attempt to add a window after the token has been
//                // invalidated. Let us hedge against that.
//                try {
//                    mWM.addView(mView, mParams);
//                    trySendAccessibilityEvent();
//                } catch (WindowManager.BadTokenException e) {
//                    /* ignore */
//                }
//            }
//        }
//
//        private void trySendAccessibilityEvent() {
//            AccessibilityManager accessibilityManager =
//                    AccessibilityManager.getInstance(mView.getContext());
//            if (!accessibilityManager.isEnabled()) {
//                return;
//            }
//            // treat toasts as notifications since they are used to
//            // announce a transient piece of information to the user
//            AccessibilityEvent event = AccessibilityEvent.obtain(
//                    AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED);
//            event.setClassName(getClass().getName());
//            event.setPackageName(mView.getContext().getPackageName());
//            mView.dispatchPopulateAccessibilityEvent(event);
//            accessibilityManager.sendAccessibilityEvent(event);
//        }
//
//        public void handleHide() {
//            if (localLOGV) Log.v(TAG, "HANDLE HIDE: " + this + " mView=" + mView);
//            if (mView != null) {
//                // note: checking parent() just to make sure the view has
//                // been added...  i have seen cases where we get here when
//                // the view isn't yet added, so let's try not to crash.
//                if (mView.getParent() != null) {
//                    if (localLOGV) Log.v(TAG, "REMOVE! " + mView + " in " + this);
//                    mWM.removeViewImmediate(mView);
//                }
//
//                mView = null;
//            }
//        }
//    }
}
