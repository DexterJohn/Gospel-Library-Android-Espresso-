/*
 * AuthorRoleManager.kt
 *
 * Generated on: 02/09/2017 11:39:54
 *
 */



package org.lds.ldssa.model.database.content.authorrole

import org.lds.ldssa.model.database.DatabaseManager
import org.lds.ldssa.util.ContentItemUtil
import javax.inject.Inject


@javax.inject.Singleton
class AuthorRoleManager @Inject constructor(databaseManager: DatabaseManager, val contentItemUtil: ContentItemUtil) : AuthorRoleBaseManager(databaseManager)