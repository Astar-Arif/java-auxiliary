package com.astar.common.library.pojo;

import com.astar.common.library.exception.AbstractException;
import com.astar.common.library.exception.AbstractRuntimeException;
import com.astar.common.library.exception.JWTAuthenticationException;

import java.util.ArrayList;
import java.util.List;

public class  Result<R> {
    private R res;
    private List<Exception> exceptions = new ArrayList<>();

    public R getResult() {
        return res;
    }

    public void setResult(R res) {
        this.res = res;
    }

    public List<Exception> getExceptions() {
        return this.exceptions;
    }

    public void addException(Exception e) {
        this.exceptions.add(e);
    }

    public boolean isExceptionExist() {
        return !this.exceptions.isEmpty();
    }

    public Exception find(String id) {
    // TODO DECIDE TO ADD HANDLING IF ID IS NULL
        for (int i = 0; i < this.exceptions.size(); i++) {
            Exception e = this.exceptions.get(i);
            if (e instanceof AbstractException ae) {
                if (id.equals(ae.getId())) return e;
            } else if (e instanceof AbstractRuntimeException are) {
                if (id.equals(are.getId())) return e;
            }
        }
        return null;
    }

    public <T> List<Exception> find(Class<T> clazz) {
        List<Exception> err = new ArrayList<>();
        for (Exception e : this.exceptions) {
            if (clazz.isInstance(e)) {
                err.add(e);
            }
        }
        return err;
    }

}
