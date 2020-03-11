package org.mybatis.scripting.velocity

import java.io.IOException
import java.io.Writer

import org.apache.velocity.context.InternalContextAdapter
import org.apache.velocity.exception.MethodInvocationException
import org.apache.velocity.exception.ParseErrorException
import org.apache.velocity.exception.ResourceNotFoundException
import org.apache.velocity.runtime.directive.Directive
import org.apache.velocity.runtime.parser.node.Node

import com.airback.db.arguments.NoValueSearchField
import org.apache.velocity.runtime.directive.DirectiveConstants

/**
 * @author airback Ltd.
 * @since 4.0
 */
class NoValExpr : Directive() {

    /*
	 * (non-Javadoc)
	 *
	 * @see org.apache.velocity.runtime.directive.Directive#getName()
	 */
    override fun getName(): String = "novalexpr"

    /*
	 * (non-Javadoc)
	 *
	 * @see org.apache.velocity.runtime.directive.Directive#getType()
	 */
    override fun getType(): Int = DirectiveConstants.BLOCK

    /*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.velocity.runtime.directive.Directive#render(org.apache.velocity
	 * .context.InternalContextAdapter, java.io.Writer,
	 * org.apache.velocity.runtime.parser.node.Node)
	 */
    @Throws(IOException::class, ResourceNotFoundException::class, ParseErrorException::class, MethodInvocationException::class)
    override fun render(context: InternalContextAdapter, writer: Writer, node: Node): Boolean {
        val value = node.jjtGetChild(0).value(context)
        if (value is NoValueSearchField) {
            val content = node.jjtGetChild(1)
            content.render(context, writer)
        }
        return true
    }
}
