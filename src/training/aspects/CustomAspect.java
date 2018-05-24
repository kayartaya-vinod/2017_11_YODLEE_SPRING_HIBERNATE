package training.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import training.dao.DaoException;

@Aspect
@Component
public class CustomAspect {

	// advice (before)
	@Before("execution( * training..*Dao.get*(..) )") // the string is the
														// pointcut information
	public void logBeforeCalling(JoinPoint jp) {
		System.out.printf("logging before calling the function %s() with %d arguments\n", jp.getSignature().getName(),
				jp.getArgs().length);
	}

	// ProceedingJoinPoint can be used only for an Around advice
	@Around("execution(* training..ProductDao.*(Double, Double))")
	public Object swapInputs(ProceedingJoinPoint jp) throws Throwable {
		try {
			Object[] args = jp.getArgs();
			Double d1 = (Double) args[0];
			Double d2 = (Double) args[1];

			if (d1 > d2) {
				args = new Object[] { d2, d1 };
			}
			return jp.proceed(args);
		} catch (Exception e) {
			throw e;
		}
	}
	
	@AfterThrowing(pointcut="execution(* training..ProductDao.*(..))", throwing="ex")
	public void convertToDaoException(Exception ex) throws DaoException {
		// System.out.println("Got an exception of type: " + ex.getClass());
		throw new DaoException(ex);
	}
}










