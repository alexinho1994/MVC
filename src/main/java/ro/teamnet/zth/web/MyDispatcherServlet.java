package ro.teamnet.zth.web;

import ro.teamnet.zth.api.annotations.MyController;
import ro.teamnet.zth.api.annotations.MyRequestMethod;
import ro.teamnet.zth.appl.controller.DepartmentController;
import ro.teamnet.zth.appl.controller.EmployeeController;
import ro.teamnet.zth.fmk.AnnotationScanUtils;
import ro.teamnet.zth.fmk.MethodAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alexandru.Grameni on 7/20/2017.
 */
public class MyDispatcherServlet extends HttpServlet{
    Map<String, MethodAttributes> methodAttributesMap = new HashMap<>();

    public void init()
    {
        try {
            Iterable<Class> classes = AnnotationScanUtils.getClasses("ro.teamnet.zth.controller");
            for(Class cls : classes)
            {
                Method[] methods = cls.getDeclaredMethods();
                for(Method  method : methods)
                {
                    MethodAttributes methodAttributes = new MethodAttributes();
                    methodAttributes.setControllerClass(cls.getName());
                    methodAttributes.setMethodName(method.getName());
                    methodAttributes.setMethodType(method.getAnnotation(MyRequestMethod.class).methodType());
                    MyController controller = (MyController) cls.getDeclaredAnnotation(MyController.class);
                    String key = controller.urlPath() +
                            method.getAnnotation(MyRequestMethod.class).urlPath() + "_"+
                            method.getAnnotation(MyRequestMethod.class).methodType();
                    methodAttributesMap.put(key, methodAttributes);
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, "GET");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatchReply(req, resp, "POST");
    }

    private void dispatchReply(HttpServletRequest request, HttpServletResponse response, String methodType)
    {
        try{
            Object resultToDisplay = dispatch(request, methodType);
            reply(response, resultToDisplay);
        } catch (Exception e)
        {
            sendExceptionError();
        }
    }

    private Object dispatch(HttpServletRequest request, String methodType)
    {
       String pathInfo = request.getPathInfo() + "_"+ methodType;
       MethodAttributes methodAttribute = methodAttributesMap.get(pathInfo);
       if(methodAttribute != null)
       {
           try {
               Class entityClass = Class.forName(methodAttribute.getControllerClass());
               Method returnMth = entityClass.getMethod(methodAttribute.getMethodName());
               return  returnMth.invoke(entityClass.newInstance());
           } catch (ClassNotFoundException e) {
               e.printStackTrace();
           } catch (NoSuchMethodException e) {
               e.printStackTrace();
           } catch (IllegalAccessException e) {
               e.printStackTrace();
           } catch (InstantiationException e) {
               e.printStackTrace();
           } catch (InvocationTargetException e) {
               e.printStackTrace();
           }
       }
        return null;
    }

    private void reply(HttpServletResponse response, Object result)
    {
        try {
            response.getWriter().write((String) result);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendExceptionError()
    {

    }


}
