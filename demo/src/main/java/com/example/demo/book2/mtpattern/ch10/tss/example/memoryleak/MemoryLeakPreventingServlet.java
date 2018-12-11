

package com.example.demo.book2.mtpattern.ch10.tss.example.memoryleak;

import com.example.demo.book2.mtpattern.ch10.tss.ManagedThreadLocal;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemoryLeakPreventingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private final static ManagedThreadLocal<Counter> TL_COUNTER = ManagedThreadLocal
	    .newInstance(new ManagedThreadLocal.InitialValueProvider<Counter>() {
		    @Override
		    protected Counter initialValue() {
			    return new Counter();
		    }
	    });

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
	    throws ServletException, IOException {

		PrintWriter pwr = resp.getWriter();
		pwr.write(TL_COUNTER.get().getAndIncrement());
		pwr.close();

	}
}
